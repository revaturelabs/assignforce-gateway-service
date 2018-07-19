FROM java:8
ADD target/gateway-service.jar .
EXPOSE 8765
CMD java -jar -Xmx1024M gateway-service.jar