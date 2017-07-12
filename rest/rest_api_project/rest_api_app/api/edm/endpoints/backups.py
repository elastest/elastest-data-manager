import logging

from flask import request
from flask_restplus import Resource
from rest_api_app.api.edm.business import create_edm_backup, update_edm_backup, delete_edm_backup
from rest_api_app.api.edm.serializers import edm_backup, page_of_edm_backups, edm_backup_resp, edm_backup_title
from rest_api_app.api.edm.parsers import pagination_arguments
from rest_api_app.api.restplus import api
from rest_api_app.database.models import Backup

log = logging.getLogger(__name__)

ns = api.namespace('edm/backups', description='Operations related to EDM backups')


@ns.route('/')
class backupsCollection(Resource):

    @api.expect(pagination_arguments)
    @api.marshal_with(page_of_edm_backups)
    def get(self):
        """
        Returns a list of existing EDM backups.
        """
        args = pagination_arguments.parse_args(request)
        page = args.get('page', 1)
        per_page = args.get('per_page', 10)

        backups_query = Backup.query
        backups_page = backups_query.paginate(page, per_page, error_out=False)

        return backups_page

    @api.expect(edm_backup_title)
    @api.marshal_with(edm_backup_resp)
    def post(self):
        """
        Creates a new EDM backup.
        """
        backup_id = create_edm_backup(request.json)
        # return {'backup_id': backup_id }, 201
        return {'backup_id': backup_id}, 201


@ns.route('/<int:id>')
@api.response(404, 'backup not found.')
class backupItem(Resource):

    @api.marshal_with(edm_backup)
    def get(self, id):
        """
        Returns the details of a specified EDM backup.
        """
        return Backup.query.filter(Backup.id == id).one()

    @api.expect(edm_backup_title)
    @api.response(204, 'backup successfully updated.')
    def put(self, id):
        """
        Updates the details of a specified EDM backup.
        """
        data = request.json
        update_edm_backup(id, data)
        return None, 204

    @api.response(204, 'backup successfully deleted.')
    def delete(self, id):
        """
        Deletes a specified EDM backup.
        """
        delete_edm_backup(id)
        return None, 204


@ns.route('/archive/<int:year>/')
@ns.route('/archive/<int:year>/<int:month>/')
@ns.route('/archive/<int:year>/<int:month>/<int:day>/')
class backupsArchiveCollection(Resource):

    @api.expect(pagination_arguments, validate=True)
    @api.marshal_with(page_of_edm_backups)
    def get(self, year, month=None, day=None):
        """
        Returns list of existing EDM backups from a specified time period.
        """
        args = pagination_arguments.parse_args(request)
        page = args.get('page', 1)
        per_page = args.get('per_page', 10)

        start_month = month if month else 1
        end_month = month if month else 12
        start_day = day if day else 1
        end_day = day + 1 if day else 31
        start_date = '{0:04d}-{1:02d}-{2:02d}'.format(year, start_month, start_day)
        end_date = '{0:04d}-{1:02d}-{2:02d}'.format(year, end_month, end_day)
        backups_query = Backup.query.filter(Backup.start_date >= start_date).filter(Backup.start_date <= end_date)

        backups_page = backups_query.paginate(page, per_page, error_out=False)

        return backups_page
