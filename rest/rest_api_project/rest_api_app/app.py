import logging.config

from flask import Flask, Blueprint
from rest_api_app import settings
from rest_api_app.api.edm.endpoints.backups import ns as edm_backups_namespace
from rest_api_app.api.edm.endpoints.restores import ns as edm_restores_namespace
from rest_api_app.api.restplus import api
from rest_api_app.database import db
from flask import json

app = Flask(__name__)
logging.config.fileConfig('logging.conf')
log = logging.getLogger(__name__)

def configure_app(flask_app):
#    flask_app.config['SERVER_NAME'] = settings.FLASK_SERVER_NAME
    flask_app.config['SERVER_HOST'] = settings.FLASK_SERVER_HOST
    flask_app.config['SERVER_PORT'] = settings.FLASK_SERVER_PORT
    flask_app.config['SQLALCHEMY_DATABASE_URI'] = settings.SQLALCHEMY_DATABASE_URI
    flask_app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = settings.SQLALCHEMY_TRACK_MODIFICATIONS
    flask_app.config['SWAGGER_UI_DOC_EXPANSION'] = settings.RESTPLUS_SWAGGER_UI_DOC_EXPANSION
    flask_app.config['RESTPLUS_VALIDATE'] = settings.RESTPLUS_VALIDATE
    flask_app.config['RESTPLUS_MASK_SWAGGER'] = settings.RESTPLUS_MASK_SWAGGER
    flask_app.config['ERROR_404_HELP'] = settings.RESTPLUS_ERROR_404_HELP


def initialize_app(flask_app):
    configure_app(flask_app)

    blueprint = Blueprint('api', __name__, url_prefix='/api')
    api.init_app(blueprint)
    api.add_namespace(edm_backups_namespace)
    api.add_namespace(edm_restores_namespace)
    flask_app.register_blueprint(blueprint)

    db.init_app(flask_app)


def main():
    initialize_app(app)
    with app.app_context():
        db.create_all()
#        log.info('>>>>> Started Exporting Swagger Api to file swagger-api.json')
#        file = open("swagger-api.json","w") 
#        file.write(json.dumps(api.__schema__)) 
#        file.close() 
#        log.info('>>>>> Finished Exporting Swagger Api to file swagger-api.json')

    log.info('>>>>> Starting development server at http://%s:%d/api/ <<<<<', settings.FLASK_SERVER_HOST, settings.FLASK_SERVER_PORT)
    app.run(host=settings.FLASK_SERVER_HOST, port=settings.FLASK_SERVER_PORT, debug=settings.FLASK_DEBUG)

if __name__ == "__main__":
    main()
