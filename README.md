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
You can use scripts in [database](src/main/resources/database) directory. \
To use them properly you need to store courses and problems in a required way.
   - All courses need to be in [database](src/main/resources/database)/courses directory.
     - For each course there needs to be corresponding .json file.
     - Each .json file has to have fields named the same as each column in database table "course" excluding column "id".
   - All problems need to be in [database](src/main/resources/database)/problems directory.
     - For each problem there needs to be corresponding directory.
     - In each directory there has to be one .json file and directory named "tests".
     - This .json file has to have fields named the same as each column in database table "problem" excluding column "id".
     - For each test for problem described in this .json file there needs to be corresponding .json file in "tests" directory.
     - Each .json file in "tests" directory has to have fields named the same as each column in database table "test" excluding column "id".

### Build and run
To build and run application you can use [build.sh](build.sh) and [run.sh](run.sh) scripts.
