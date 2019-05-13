FROM andreptb/oracle-java:8-alpine
MAINTAINER CAS <cas@guadaltel.es>

# Wilfly version
ENV WILDFLY_VERSION 10.1.0.Final

ENV JBOSS_HOME /opt/wildfly-10.1.0.Final

# Installs WILDFLY
RUN apk add --no-cache --virtual=build-dependencies wget && \
    cd "/tmp" && \
    wget -q http://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz && \
    tar xzvf wildfly-$WILDFLY_VERSION.tar.gz && \
    mkdir -p $JBOSS_HOME && \
    mv /tmp/wildfly-$WILDFLY_VERSION /opt && \
    apk del build-dependencies && \
    rm "/tmp/"*

# Add postgresql jdbc module
ADD modules/org/postgresql/main/module.xml $JBOSS_HOME/modules/system/layers/base/org/postgresql/main/module.xml
ADD modules/org/postgresql/main/postgresql-9.4-1200.jdbc4.jar $JBOSS_HOME/modules/system/layers/base/org/postgresql/main/postgresql-9.4-1200.jdbc4.jar
ADD modules/org/postgresql/main/postgresql-42.0.0.jar $JBOSS_HOME/modules/system/layers/base/org/postgresql/main/postgresql-42.0.0.jar
#ADD modules/org/postgresql/main/postgis-jdbc-2.2.0.jar $JBOSS_HOME/modules/system/layers/base/org/postgresql/main/postgis-jdbc-2.2.0.jar

# Add custom standalone configuration
ADD standalone.xml $JBOSS_HOME/standalone/configuration/standalone.xml
ADD hcsigc.ear $JBOSS_HOME/standalone/deployments/hcsigc.ear

EXPOSE 8080
CMD ["/opt/wildfly-10.1.0.Final/bin/standalone.sh", "-b", "0.0.0.0"]