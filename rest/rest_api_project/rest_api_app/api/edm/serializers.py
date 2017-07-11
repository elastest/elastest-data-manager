from flask_restplus import fields
from rest_api_app.api.restplus import api

edm_backup_resp = api.model('EDM backup response', {
    'backup_id': fields.Integer(readOnly=True, description='The unique identifier of an EDM backup')
})

edm_backup = api.model('edm backup', {
    'id': fields.Integer(readOnly=True, description='The unique identifier of an EDM backup'),
    'title': fields.String(required=True, description='EDM backup title'),
    'filepath': fields.String(required=True, description='EDM backup file path'),
    'start_date': fields.DateTime,
    # 'status': fields.String(attribute='restore.id'),
})

pagination = api.model('A page of results', {
    'page': fields.Integer(description='Number of this page of results'),
    'pages': fields.Integer(description='Total number of pages of results'),
    'per_page': fields.Integer(description='Number of items per page of results'),
    'total': fields.Integer(description='Total number of results'),
})

page_of_edm_backups = api.inherit('Page of edm backups', pagination, {
    'items': fields.List(fields.Nested(edm_backup))
})

restore = api.model('edm restore', {
    'id': fields.Integer(readOnly=True, description='The unique identifier of a edm restore'),
    'name': fields.String(required=True, description='Restore name'),
    'backup_id': fields.Integer(required=True, description='Backup id')
})

restore_with_backups = api.inherit('edm restore with backups', restore, {
    'backups': fields.List(fields.Nested(edm_backup))
})
