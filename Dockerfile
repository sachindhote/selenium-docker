FROM bellsoft/liberica-openjdk-alpine:17.0.8

# install curl and jq
RUN apk add curl jq

#Create workspace
WORKDIR /home/selenium-docker

#Add required file to above directory to run the test
ADD target/docker-resources     ./
ADD runner.sh                   runner.sh

#  environment variables
#     HUB_HOST
#     BROWSER
#     THREAD_COUNT
#     TEST_SUITE

# run the test
# ENTRYPOINT java -cp 'libs/*' \
#            -Dselenium.grid.enabled=true \
#            -Dselenium.grid.hubHost=${HUB_HOST} \
#            -Dbrowser=${BROWSER} \
#            org.testng.TestNG \
#            -threadcount ${THREAD_COUNT} \
#            test-suites/${TEST_SUITE}

# start the runner.sh
ENTRYPOINT sh runner.sh