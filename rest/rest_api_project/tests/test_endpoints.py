from rest_api_app import app
from flask import url_for

from rest_api_app.api.restplus import api
# from rest_api_app import api
from rest_api_app.database import db

from flask import Flask, Blueprint
from rest_api_app.api.edm.endpoints.backups import ns as edm_backups_namespace
from rest_api_app.api.edm.endpoints.restores import ns as edm_restores_namespace
import flask_restplus as restplus
from rest_api_app import settings

import os
import unittest
import json
import requests

class EdmRestApiTest(unittest.TestCase):
	############################
    #### setup and teardown ####
    ############################

    # executed prior to each test
    def setUp(self):
        tapp = Flask(__name__)
        # blueprint = Blueprint('api', __name__, url_prefix='/api')
        # api.init_app(blueprint)
        # api.add_namespace(edm_backups_namespace)
        # api.add_namespace(edm_restores_namespace)
        # tapp.register_blueprint(blueprint)
        # db.init_app(tapp)
        app.initialize_app(tapp)
        with tapp.app_context():
            db.create_all()

        # super(ApiTests, self).setUp()
        tapp.config['TESTING'] = True
        tapp.config['WTF_CSRF_ENABLED'] = False
        tapp.config['DEBUG'] = False

        # app.app.register_blueprint(app.blueprint)
        # self.assertEquals(app.debug, False)

        self.app = tapp.test_client()
        self.assertEquals(tapp.debug, False)



    # executed after each test
    def tearDown(self):
        pass

	######################
	#### Backup tests ####
	######################

    def test_01_post_backups(self):
        url = 'http://localhost:8000/api/edm/backups/'
        testheaders = { 'Content-Type': 'application/json', 'Accept': 'application/json'}
        testdata = { "title": "Test Backup 1" }
        # response = requests.post(url, headers=testheaders, data=json.dumps(testdata))
        # self.assertEqual(response.status_code, 201)
        response = self.app.post('/api/edm/backups/', headers=testheaders, data=json.dumps(testdata))
        self.assertEqual(response.status_code, 201)

    def test_02_get_backups(self):
        # url = 'http://localhost:8000/api/edm/backups/'
        # response = requests.get(url)
        # self.assertEqual(response.status_code, 200)
        # response = self.app.get('/api/edm/backups/', follow_redirects=True, content_type='application/json')
        # print(response.data)
        # self.assertEqual(response.status_code, 200)
        response = self.app.get('/api/edm/backups/', follow_redirects=True)
        # print(response.data)
        self.assertEqual(response.status_code, 200)


    def test_03_get_backups_pagination(self):
        url = 'http://localhost:8000/api/edm/backups/?page=1&per_page=10'
        response = requests.get(url)

    def test_04_get_specific_backup(self):
        # url = 'http://localhost:8000/api/edm/backups/1'
        # response = requests.get(url)
        # self.assertEqual(response.status_code, 200)
        response = self.app.get('/api/edm/backups/1', follow_redirects=True)
        # print(response.data)
        self.assertEqual(response.status_code, 200)

    def test_05_put_specific_backup(self):
        url = 'http://localhost:8000/api/edm/backups/1'
        testheaders = { 'Content-Type': 'application/json', 'Accept': 'application/json'}
        testdata = { "title": "Test Updated Backup 1" }
        # response = requests.put(url, headers=testheaders, data=json.dumps(testdata))
        # self.assertEqual(response.status_code, 204)
        response = self.app.put('/api/edm/backups/1', headers=testheaders, data=json.dumps(testdata))
        self.assertEqual(response.status_code, 204)

    def test_06_get_backup_archive_yy(self):
        # url = 'http://localhost:8000/api/edm/backups/archive/1'
        # response = requests.get(url)
        # self.assertEqual(response.status_code, 200)
        response = self.app.get('/api/edm/backups/archive/1', follow_redirects=True)
        # print(response.data)
        self.assertEqual(response.status_code, 200)

    def test_07_get_backup_archive_yymm(self):
        url = 'http://localhost:8000/api/edm/backups/archive/1999/12'
        response = requests.get(url)
        self.assertEqual(response.status_code, 200)

    def test_08_get_backup_archive_yymmdd(self):
        url = 'http://localhost:8000/api/edm/backups/archive/1999/12/31'
        response = requests.get(url)
        self.assertEqual(response.status_code, 200)

    #######################
    #### Restore tests ####
    #######################

    # def test_51_post_restores(self):
    #     url = 'http://localhost:8000/api/edm/restores/'
    #     testheaders = { 'Content-Type': 'application/json', 'Accept': 'application/json'}
    #     testdata = { "backup_id": 1, "title": "Test Restore 1" }
    #     # response = requests.post(url, headers=testheaders, data=json.dumps(testdata))
    #     # self.assertEqual(response.status_code, 201)
    #     response = self.app.post('/api/edm/restores/', headers=testheaders, data=json.dumps(testdata))
    #     self.assertEqual(response.status_code, 201)
    #
    # def test_52_get_restores(self):
    #     # url = 'http://localhost:8000/api/edm/restores/'
    #     # response = requests.get(url)
    #     # self.assertEqual(response.status_code, 200)
    #     response = self.app.get('/api/edm/restores/', follow_redirects=True)
    #     # print(response.data)
    #     self.assertEqual(response.status_code, 200)
    #
    # def test_53_get_restores_pagination(self):
    #     url = 'http://localhost:8000/api/edm/restores/?page=1&per_page=10'
    #     response = requests.get(url)
    #     self.assertEqual(response.status_code, 200)
    #
    # def test_54_get_specific_restore(self):
    #     # url = 'http://localhost:8000/api/edm/restores/1'
    #     # response = requests.get(url)
    #     # self.assertEqual(response.status_code, 200)
    #     response = self.app.get('/api/edm/restores/1', follow_redirects=True)
    #     # print(response.data)
    #     self.assertEqual(response.status_code, 200)
    #
    # def test_55_put_specific_restore(self):
    #     url = 'http://localhost:8000/api/edm/restores/1'
    #     testheaders = { 'Content-Type': 'application/json', 'Accept': 'application/json'}
    #     testdata = { "title": "Test Updated Restore 1" }
    #     # response = requests.put(url, headers=testheaders, data=json.dumps(testdata))
    #     # self.assertEqual(response.status_code, 204)
    #     response = self.app.put('/api/edm/restores/1', headers=testheaders, data=json.dumps(testdata))
    #     self.assertEqual(response.status_code, 204)



    # def test_91_delete_specific_backup(self):
    #     response = self.app.delete('/api/edm/backups/1', follow_redirects=True)
    #     self.assertEqual(response.status_code, 204)
    #
    # def test_92_delete_specific_restore(self):
    #     response = self.app.delete('/api/edm/restores/1', follow_redirects=True)
    #     self.assertEqual(response.status_code, 204)

if __name__ == '__main__':
    unittest.main()
