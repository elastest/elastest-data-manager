from rest_api_app import app
# from flask import url_for
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
        app.app.config['TESTING'] = True
        app.app.config['DEBUG'] = False
        self.app = app.app.test_client()
        self.assertEquals(app.app.debug, False)

 
    # executed after each test
    def tearDown(self):
        pass

	###############
	#### tests ####
	###############
 
    def test_01_post_backups(self):
        url = 'http://localhost:8000/api/edm/backups/'
        testheaders = { 'Content-Type': 'application/json', 'Accept': 'application/json'}
        testdata = { "filepath": "string", "id": 0, "start_date": "2017-07-11T09:33:51.878Z", "title": "string" }
        response = requests.post(url, headers=testheaders, data=json.dumps(testdata))
        # json_data = json.loads(response.text)
        # print("json_data="+str(json_data))
        # print("json_data['backup_id']="+str(json_data['backup_id']))
        self.assertEqual(response.status_code, 201)

    def test_02_get_backups(self):
        url = 'http://localhost:8000/api/edm/backups/'
        response = requests.get(url)
        # print("response.json="+str(response.json))
        self.assertEqual(response.status_code, 200)

    def test_03_get_backups_pagination(self):
        url = 'http://localhost:8000/api/edm/backups/?page=1&per_page=10'
        response = requests.get(url)
        # print("response.json="+str(response.json))
        self.assertEqual(response.status_code, 200)

    def test_04_get_specific_backup(self):
        url = 'http://localhost:8000/api/edm/backups/1'
        response = requests.get(url)
        # print("response.json="+str(response.json))
        self.assertEqual(response.status_code, 200)

    def test_05_put_specific_backup(self):
        url = 'http://localhost:8000/api/edm/backups/1'
        testheaders = { 'Content-Type': 'application/json', 'Accept': 'application/json'}
        testdata = { "filepath": "string2", "id": 0, "start_date": "2017-07-11T09:33:51.878Z", "title": "string2" }
        response = requests.put(url, headers=testheaders, data=json.dumps(testdata))
        # print("response.json="+str(response.json))
        self.assertEqual(response.status_code, 204)

    def test_06_delete_specific_backup(self):
        response = self.app.delete('/api/edm/backups/1', follow_redirects=True)
        self.assertEqual(response.status_code, 404)

    def test_07_get_backup_archive_yy(self):
        url = 'http://localhost:8000/api/edm/backups/archive/1'
        response = requests.get(url)
        # print("response.json="+str(response.json))
        self.assertEqual(response.status_code, 200)

    def test_08_get_backup_archive_yymm(self):
        url = 'http://localhost:8000/api/edm/backups/archive/1999/12'
        response = requests.get(url)
        # print("response.json="+str(response.json))
        self.assertEqual(response.status_code, 200)

    def test_09_get_backup_archive_yymmdd(self):
        url = 'http://localhost:8000/api/edm/backups/archive/1999/12/31'
        response = requests.get(url)
        # print("response.json="+str(response.json))
        self.assertEqual(response.status_code, 200)


if __name__ == '__main__':
    unittest.main()
