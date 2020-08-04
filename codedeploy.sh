#!/usr/bin/bash 

cd /opt/glassfish5/glassfish/domains/domain1/docroot/

sudo rm GradeBook-1.0-SNAPSHOT.war

echo 'old copy of gradebook deleted from docroot'

asadmin undeploy GradeBook-1.0-SNAPSHOT

mv ~/GradeBook-1.0-SNAPSHOT.war /opt/glassfish5/glassfish/domains/domain1/docroot/

echo 'new copy of gradebook copied to docroot'

asadmin deploy --contextroot GradeBook GradeBook-1.0-SNAPSHOT.war

wget http://ipaddress:8080/GradeBook/resources/clearall

wget wget http://ipaddress:8080/GradeBook/resources/server

