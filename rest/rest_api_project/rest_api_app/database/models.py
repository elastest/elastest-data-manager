# The examples in this file come from the Flask-SQLAlchemy documentation
# For more information take a look at:
# http://flask-sqlalchemy.pocoo.org/2.1/quickstart/#simple-relationships

from datetime import datetime

from rest_api_app.database import db


class Backup(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(80))
    start_date = db.Column(db.DateTime)
    status = db.Column(db.String(80))

    def __init__(self, title, status, start_date=None):
        self.title = title
        if start_date is None:
            start_date = datetime.utcnow()
        self.start_date = start_date
        self.status = status

    def __repr__(self):
        return '<Backup %r>' % self.title


class Restore(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(50))
    backup_id = db.Column(db.Integer)

    def __init__(self, title, backup_id):
        self.title = title
        self.backup_id = backup_id

    def __repr__(self):
        return '<Restore %r>' % self.title
