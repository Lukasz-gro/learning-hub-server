# learning-hub-server

### Prerequisites
- **Maven 3.9+**
- **JDK 17+**
- **Docker**
- **PostgreSQL database**

### Overall Setup
You need to export necessary environment variables. \
To do so you can modify our [learning-hub-setup.sh](src/main/resources/setup/learning-hub-setup.sh) script.

### Docker Setup
1. **Build docker image** \
You can use [buildImage.sh](src/main/resources/setup/buildImage.sh) script.

### Database Setup
1. **Initialize database** \
You can use [create.sql](src/main/resources/setup/create.sql) script.

2. **Insert data into tables** \
You can use [sample_data.sql](src/main/resources/setup/sample_data.sql) script.

### Build and run
To build and run application you can use [build.sh](build.sh) and [run.sh](run.sh) scripts.
