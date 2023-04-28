# learning-hub-server

### Prerequisites
- **Maven 3.9+**
- **JDK 17+**
- **Docker**
- **PostgreSQL database**

### Docker Setup

1. **Export DOCKER_DIR_PATH** \
e.g. export DOCKER_DIR_PATH=/path/to/project/directory/src/main/resources/docker/

2. **Build docker image** \
You can use [buildImage.sh](src/main/resources/setup/docker/buildImage.sh) script.

### Database Setup

1. **Database properties** \
Enter your database server properties for JPA. \
They are in [application.properties](src/main/resources/application.properties).

2. **Initialize database** \
You can use [create.sql](src/main/resources/setup/database/create.sql) script.

3. **Insert data into tables** \
You can use [sample_data.sql](src/main/resources/setup/database/sample_data.sql) script.


### Build and run
To build and run application you can use [build.sh](build.sh) and [run.sh](run.sh) scripts.
