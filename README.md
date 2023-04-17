# learning-hub-server


It is recommended to add all exports to  ~/.bashrc file and reboot.

### Docker Setup

1. **Export DOCKER_DIR_PATH** \
e.g. export DOCKER_DIR_PATH=/home/ubuntu/docker/

2. **Build docker image** \
You can use buildImage.sh script in src/main/resources/setup/docker directory.

### Database Setup

1. **Export DATABASE_USER** \
e.g. export DATABASE_USER=postgres

2. **Export DATABASE_PASSWORD** \
e.g. export DATABASE_PASSWORD=password

3. **Create tables** \
You can use create.sql script in src/main/resources/setup/database directory. \
It is recommended you use clear.sql script in the same directory before create.sql

4. **Insert data into tables** \
You can use insert.sql script in src/main/resources/setup/database directory.
