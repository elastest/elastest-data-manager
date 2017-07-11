import logging

from flask import request
from flask_restplus import Resource
from rest_api_app.api.edm.business import create_restore, delete_restore, update_restore
from rest_api_app.api.edm.serializers import restore, restore_with_backups
from rest_api_app.api.restplus import api
from rest_api_app.database.models import Restore

log = logging.getLogger(__name__)

ns = api.namespace('edm/restores', description='Operations related to edm restores')


@ns.route('/')
class RestoreCollection(Resource):

    @api.marshal_list_with(restore)
    def get(self):
        """
        Returns list of edm restores.
        """
        restores = Restore.query.all()
        return restores

    @api.response(201, 'Restore successfully created.')
    @api.expect(restore)
    def post(self):
        """
        Creates a new edm restore.
        """
        data = request.json
        create_restore(data)
        return None, 201


@ns.route('/<int:id>')
@api.response(404, 'Restore not found.')
class RestoreItem(Resource):

    @api.marshal_with(restore_with_backups)
    def get(self, id):
        """
        Returns a restore with a list of backups.
        """
        return Restore.query.filter(Restore.id == id).one()

    @api.expect(restore)
    @api.response(204, 'Restore successfully updated.')
    def put(self, id):
        """
        Updates a edm restore.

        Use this method to change the name of a edm restore.

        * Send a JSON object with the new name in the request body.

        ```
        {
          "name": "New Restore Name"
        }
        ```

        * Specify the ID of the restore to modify in the request URL path.
        """
        data = request.json
        update_restore(id, data)
        return None, 204

    @api.response(204, 'Restore successfully deleted.')
    def delete(self, id):
        """
        Deletes edm restore.
        """
        delete_restore(id)
        return None, 204
