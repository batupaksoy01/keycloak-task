FROM quay.io/keycloak/keycloak:18.0.0

COPY ./plugins /opt/keycloak/providers

RUN curl -sL https://github.com/aerogear/keycloak-metrics-spi/releases/download/2.5.3/keycloak-metrics-spi-2.5.3.jar -o /opt/keycloak/providers/keycloak-metrics-spi.jar

RUN /opt/keycloak/bin/kc.sh build

WORKDIR /opt/keycloak

# Use ./certs to put certificates to be used 
# COPY ./certs ./

# Generate certificate if we don't have
RUN keytool -genkeypair -storepass password -storetype PKCS12 -keyalg RSA -keysize 2048 -dname "CN=server" -alias server -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore conf/server.keystore

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start"]