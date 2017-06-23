node('docker'){
    stage "Container Prep"
        echo("the node is up")
        def mycontainer = docker.image('sgioldasis/ci-docker-in-docker:latest')
        mycontainer.pull() // make sure we have the latest available from Docker Hub
        mycontainer.inside("-u jenkins -v /var/run/docker.sock:/var/run/docker.sock:rw") {
            git 'https://github.com/elastest/elastest-data-manager.git'
            
            // stage "Test"
            //     sh 'ls -la'
            //     echo ("Starting maven tests")
            //     echo ("No tests yet, but these would be integration at least")
            //     sh 'which docker'
                
            stage "Build Alluxio image - Package"
                echo ("building..")
                //need to be corrected to the organization because at the moment elastestci can't create new repositories in the organization
                def alluxio_image = docker.build("sgioldasis/elastest-alluxio:1.5.0","./alluxio")

            stage "Build Hadoop image - Package"
                echo ("building..")
                //need to be corrected to the organization because at the moment elastestci can't create new repositories in the organization
                def hadoop_image = docker.build("sgioldasis/elastest-hadoop:2.8.0","./hadoop")

            stage "Build Elasticsearch image - Package"
                echo ("building..")
                //need to be corrected to the organization because at the moment elastestci can't create new repositories in the organization
                def elasticsearch_image = docker.build("sgioldasis/elastest-elasticsearch:5.4.1","./elasticsearch")

            stage "Build Kibana image - Package"
                echo ("building..")
                //need to be corrected to the organization because at the moment elastestci can't create new repositories in the organization
                def kibana_image = docker.build("sgioldasis/elastest-kibana:5.4.1","./kibana")

            stage "Build Cerebro image - Package"
                echo ("building..")
                //need to be corrected to the organization because at the moment elastestci can't create new repositories in the organization
                def cerebro_image = docker.build("sgioldasis/elastest-cerebro:0.6.5","./cerebro")

            stage "Build MySQL image - Package"
                echo ("building..")
                //need to be corrected to the organization because at the moment elastestci can't create new repositories in the organization
                def mysql_image = docker.build("sgioldasis/elastest-mysql:5.7","./mysql")

            stage "Run docker-compose"
            //    myimage.run()
            //    sh 'chmod +x bin/startup-linux.sh && bin/startup-linux.sh'
                sh 'pwd'
                sh 'chmod +x alluxio/entrypoint.sh'
                sh 'docker-compose up -d'
                echo ("System is running..")
                
            stage "publish"
                echo ("publishing..")
            // //this is work arround as withDockerRegistry is not working properly 
            withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'sgioldasis-dockerhub',
                usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                sh 'docker login -u "$USERNAME" -p "$PASSWORD"'
                alluxio_image.push()
                hadoop_image.push()
                elasticsearch_image.push()
                kibana_image.push()
                cerebro_image.push()
                mysql_image.push()
             }
        }
}