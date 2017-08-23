from flask import Flask, Blueprint, json
from rest_api_app.api.restplus import api
from rest_api_app import settings
from rest_api_app.api.edm.endpoints.backups import ns as edm_backups_namespace
from rest_api_app.api.edm.endpoints.restores import ns as edm_restores_namespace

app = Flask(__name__)
app.config['SERVER_NAME'] = 'localhost'
app.config['SERVER_HOST'] = settings.FLASK_SERVER_HOST
app.config['SERVER_PORT'] = settings.FLASK_SERVER_PORT
app.config['SQLALCHEMY_DATABASE_URI'] = settings.SQLALCHEMY_DATABASE_URI
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = settings.SQLALCHEMY_TRACK_MODIFICATIONS
app.config['SWAGGER_UI_DOC_EXPANSION'] = settings.RESTPLUS_SWAGGER_UI_DOC_EXPANSION
app.config['RESTPLUS_VALIDATE'] = settings.RESTPLUS_VALIDATE
app.config['RESTPLUS_MASK_SWAGGER'] = settings.RESTPLUS_MASK_SWAGGER
app.config['ERROR_404_HELP'] = settings.RESTPLUS_ERROR_404_HELP

blueprint = Blueprint('api', __name__, url_prefix='/api')
api.init_app(blueprint)
api.add_namespace(edm_backups_namespace)
api.add_namespace(edm_restores_namespace)
app.register_blueprint(blueprint)

with app.app_context():
    # print(json.dumps(api.__schema__))
    with open('api.json', 'w') as outfile:
        json.dump(api.__schema__, outfile)

print("Exported file api.json ...")
