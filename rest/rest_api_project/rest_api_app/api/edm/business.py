from rest_api_app.database import db
from rest_api_app.database.models import Backup, Restore
from rest_api_app.utils import esutils
import sys, os, subprocess, tarfile


def create_edm_backup(data):
    title = data.get('title')
    status = 'INITIATED'
    backup = Backup(title, status)
    db.session.add(backup)
    db.session.commit()
    print("=============> Starting Backup - backup_id = "+str(backup.id))
    print("=============> Backing up - Elasticsearch ...")
    config = esutils.read_config()
    esutils.snapshot_indices_from_src_to_fs(config, backup.id)
    print("=============> Backing up - HDFS ...")
    cmd = "alluxio fs copyToLocal /hdfs /backup/hdfs/"+str(backup.id)
    subprocess.check_output([cmd], shell=True)
    print("=============> Backing up - MySQL ...")
    bfile =  'elastest_mysql.sql'
    cnf = 'mysqlbackup.cnf'
    dumpdir = "/backup/mysql/"+str(backup.id)
    print("=============> dumpdir = "+dumpdir)
    if not os.path.exists(dumpdir):
      print("=============> Creating dumpdir - "+dumpdir+" ...")
      os.makedirs(dumpdir)
    dumpfilepath = dumpdir + '/' + bfile
    dumpfile = open(dumpfilepath, 'w')
    print("=============> dumpfile = "+str(dumpfile))
    cmd = ['mysqldump', '--defaults-extra-file='+cnf, '--all-databases', '--events']
    p = subprocess.Popen(cmd, stdout=dumpfile)
    retcode = p.wait()
    dumpfile.close()
    if retcode > 0:
      print('Error: MySQL Backup error')
    return backup.id

def update_edm_backup(backup_id, data):
    backup = Backup.query.filter(Backup.id == backup_id).one()
    backup.title = data.get('title')
    backup.status = 'UPDATED'
    # backup.restore = Restore.query.filter(Restore.id == restore_id).one()
    db.session.add(backup)
    db.session.commit()


def delete_edm_backup(backup_id):
    backup = Backup.query.filter(Backup.id == backup_id).one()
    db.session.delete(backup)
    db.session.commit()


def create_restore(data):
    restore_title = data.get('title')
    restore_id = data.get('id')
    backup_id = data.get('backup_id')

    restore = Restore(restore_title, backup_id)
    if restore_id:
        restore.id = restore_id

    db.session.add(restore)
    db.session.commit()
    print("=============> Starting Restore - backup_id = "+str(backup_id))
    print("=============> Restoring - Elasticsearch ...")
    config = esutils.read_config()
    esutils.restore_indices_from_fs_to_dest(config, backup_id)
    print("=============> Restoring - HDFS ...")
    cmd = "alluxio fs copyFromLocal /backup/hdfs/"+str(backup_id)+" /hdfs"
    subprocess.check_output([cmd], shell=True)
    print("=============> Restoring - MySQL ...")
    bfile =  'elastest_mysql.sql'
    cnf = 'mysqlbackup.cnf'
    dumpdir = "/backup/mysql/"+str(backup_id)
    print("=============> dumpdir = "+dumpdir)
    dumpfilepath = dumpdir + '/' + bfile
    print("=============> dumpfilepath = "+str(dumpfilepath))
    cmd = ['mysql', '--defaults-extra-file='+cnf, '-e', 'source '+dumpfilepath]
    p = subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    stdout, stderr = p.communicate()
    if p.returncode > 0:
      print('MySQL Restore Error:')
      print(stderr)
      sys.exit(1)


def update_restore(restore_id, data):
    restore = Restore.query.filter(Restore.id == restore_id).one()
    restore.title = data.get('title')
    db.session.add(restore)
    db.session.commit()


def delete_restore(restore_id):
    restore = Restore.query.filter(Restore.id == restore_id).one()
    db.session.delete(restore)
    db.session.commit()
