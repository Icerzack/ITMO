FROM quay.io/wildfly/wildfly:26.1.1.Final
COPY deployment/standalone.xml /opt/jboss/wildfly/standalone/configuration/

COPY api/target/api-1.war /opt/jboss/wildfly/standalone/deployments/
COPY logic/target/logic-1-jar-with-dependencies.jar /opt/jboss/wildfly/standalone/deployments/
COPY deployment/postgresql-42.5.0.jar /opt/jboss/wildfly/standalone/deployments/
