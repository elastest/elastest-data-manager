node('docker'){
    stage "Container Prep"
        echo("the node is up")
        sh 'echo 262144 | sudo tee /proc/sys/vm/max_map_count'
        //sysctl -w vm.max_map_count=262144
        //sysctl vm.max_map_count
        def mycontainer = docker.image('sgioldasis/ci-docker-in-docker:latest')
        //def mycontainer = docker.image('elastest/docker-siblings:latest')
        mycontainer.pull() // make sure we have the latest available from Docker Hub
        mycontainer.inside("-u jenkins -v /var/run/docker.sock:/var/run/docker.sock:rw") {
            git 'https://github.com/elastest/elastest-data-manager.git'

            // stage "Test"
            //     sh 'ls -la'
            //     echo ("Starting maven tests")
            //     echo ("No tests yet, but these would be integration at least")
            //     sh 'which docker'

            stage "Cobertura"
                echo "$COB_EDM_TOKEN"
                
                def exitCode = sh(
                    returnStatus: true,
                    //script: "curl -s https://raw.githubusercontent.com/codecov/codecov-bash/master/codecov | bash -s - $codecovArgs")
                    script: " pip install --user codecov && codecov -v -t $COB_EDM_TOKEN")
                    if (exitCode != 0) {
                        echo( exitCode +': Failed to upload code coverage to codecov')
                    }
                
            stage "Build Rest API image - Package"
                echo ("building..")
                //need to be corrected to the organization because at the moment elastestci can't create new repositories in the organization
                def rest_api_image = docker.build("elastest/edm-rest-api:0.5.0","./rest")

            stage "Build Alluxio image - Package"
                echo ("building..")
                sh 'chmod +x alluxio/entrypoint.sh'
                //need to be corrected to the organization because at the moment elastestci can't create new repositories in the organization
                def alluxio_image = docker.build("elastest/edm-alluxio:0.5.0","./alluxio")

            stage "Build Hadoop image - Package"
                echo ("building..")
                //need to be corrected to the organization because at the moment elastestci can't create new repositories in the organization
                def hadoop_image = docker.build("elastest/edm-hadoop:0.5.0","./hadoop")

            stage "Build Elasticsearch image - Package"
                echo ("building..")
                //need to be corrected to the organization because at the moment elastestci can't create new repositories in the organization
                def elasticsearch_image = docker.build("elastest/edm-elasticsearch:0.5.0","./elasticsearch")

            stage "Build Kibana image - Package"
                echo ("building..")
                //need to be corrected to the organization because at the moment elastestci can't create new repositories in the organization
                def kibana_image = docker.build("elastest/edm-kibana:0.5.0","./kibana")

            stage "Build Cerebro image - Package"
                echo ("building..")
                //need to be corrected to the organization because at the moment elastestci can't create new repositories in the organization
                def cerebro_image = docker.build("elastest/edm-cerebro:0.5.0","./cerebro")

            stage "Build MySQL image - Package"
                echo ("building..")
                //need to be corrected to the organization because at the moment elastestci can't create new repositories in the organization
                def mysql_image = docker.build("elastest/edm-mysql:0.5.0","./mysql")

            stage "Run EDM docker-compose"
                sh 'chmod +x bin/* && bin/teardown-ci.sh && bin/startup-ci.sh'
                echo ("EDM System is running..")
                
            stage "Unit tests"
                echo ("Starting unit tests...")
                sh 'bin/run-tests.sh'
                step([$class: 'JUnitResultArchiver', testResults: '**/rest/rest_api_project/nosetests.xml'])

            stage "publish"
                echo ("publishing..")
                withCredentials([[
                    $class: 'UsernamePasswordMultiBinding', 
                    credentialsId: 'elastestci-dockerhub',
                    usernameVariable: 'USERNAME',
                    passwordVariable: 'PASSWORD']]) {
                        sh 'docker login -u "$USERNAME" -p "$PASSWORD"'
                        //here your code 
                        rest_api_image.push()
                        alluxio_image.push()
                        hadoop_image.push()
                        elasticsearch_image.push()
                        kibana_image.push()
                        cerebro_image.push()
                        mysql_image.push()
                    }

        }
}