# All Possible Runs are as follows

## This command will run by default start a full execution
## from src/test/resources/test-suites/FULL_RUN.xml suite file in DEV environment.
# mvn test

## If you want to run in another environment please use the following command
## e.g. uat
# mvn test -Puat

## If you want to run a specific suite xml file please use the following command in DEV environment
## e.g. src/test/resources/test-suites/TS_API_SmokeTests.xml
# mvn test -Dsuite.xml=<path to suite xml file>

## If you want to run grouped tests please use the following command
## e.g. group is SMOKE, you can use more than one group
# mvn test -Dgroups=SMOKE

## If you want to run a particular test class please use the following command
## e.g. class is TC_API_SmokeTest
# mvn test -Dtest=TC_API_SmokeTest

## You can combine all the possible flags in one command
# mvn test -Puat -Dgroups=SMOKE -Dtest=TC_API_SmokeTest -Dsuite.xml=src/test/resources/test-suites/TS_API_Users.xml

LOGS_DIR='./logs'

function print_header {
    echo "========================================================"
    echo $1
    echo "========================================================"
}

function create_logs_dir {
    print_header 'Ensure logs directory exists'
    mkdir -p ${LOGS_DIR} || return 1
    echo "Log directory created at: $(pwd)/${LOGS_DIR}"
}

function build_project {
    print_header 'Building Project'
    mvn clean install -DskipTests > ${LOGS_DIR}/build.log
    echo "Build log created at: $(pwd)/${LOGS_DIR}/build.log"
}

function run_tests {
    print_header 'Running Tests'
    mvn test -Pdev > ${LOGS_DIR}/testsExecution.log 2>&1 || return 1
    echo "Test execution log created at: $(pwd)/${LOGS_DIR}/testsExecution.log"
}

create_logs_dir || print_header "Creating Log Folder Failed"
build_project || { print_header "Building Project Failed" ; exit 1; }
run_tests || print_header "Running Tests Failed"
