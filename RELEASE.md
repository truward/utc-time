How to release
==============

## Operation Sequence

### Perform release

This is performs an automated process of releasing project artifacts, including version
updates, signing and publishing artifacts.

``
$ mvn release:clean release:prepare -P release

$ mvn release:perform -P release
``

In case of 'Peer not authenticated' error try

``
mvn -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true release:perform -P release
``

### Set version

This should be used to set new version for all the projects.

``
mvn versions:set -DnewVersion=1.0.3 -P release
``


## Miscellaneous

### Prepare the release

``
mvn release:prepare -Prelease
``

See release plugin info on apache.maven.org

### Deploy

See intructions at http://central.sonatype.org/pages/apache-maven.html


