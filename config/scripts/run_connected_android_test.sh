#!/bin/bash

# This script launches all the available AVDs (Android Virtual Devices) configured on the local machine,
# and runs the module's connected android tests, if exists.

# The script excepts the below variables to be exported:
# $ANDROID_SDK_HOME - The absolute path the the android sdk directory.

# Execute the script like so:
# export ANDROID_SDK_HOME="/path/to/Android/sdk"
# ./config/scripts/run_connected_android_test.sh

# Notice: In order of the tests to pass, it is recommended to disable all animations on each of the AVDs in it's developer options.

echo "PWD=`pwd`"

MODULE_NAME=$1
MODULE_VERSION=$2

echo "MODULE_NAME=$MODULE_NAME"
echo "MODULE_VERSION=$MODULE_VERSION"

GRADLE="./gradlew"

if [[ ! -f "$ADB" ]]; then
    ADB="/home/$USER/Development/Android/sdk/platform-tools/adb"
fi

if [[ ! -d "$ANDROID_SDK_HOME" ]]; then
    ANDROID_SDK_HOME="/home/$USER/Development/Android/sdk"
fi

EMULATOR="$ANDROID_SDK_HOME/emulator/emulator"
EMULATOR_ID=emulator-5554

echo "GRADLE=$GRADLE"
echo "ADB=$ADB"
echo "ANDROID_SDK_HOME=$ANDROID_SDK_HOME"
echo "EMULATOR=$EMULATOR"
echo "EMULATOR_ID=$EMULATOR_ID"

command -v ${EMULATOR} >/dev/null 2>&1 || { printf >&2 "\nError: couldn't find android emulator, invalid path [$EMULATOR].\n"; exit 1; }

# Functions

function check_emulators_availability() {
    local avd_names=("$@")

    for avd_name in ${avd_names[@]}; do
        ${EMULATOR} -list-avds | grep "${avd_name}" >/dev/null || { printf "\nError: couldn't find android virtual device [$avd_name].\n"; exit 1; }
    done
}

function start_android_emulator() {
    local avd_name=$1

    printf "\n> Starting android emulator [$avd_name]...\n"

    ${EMULATOR} -avd ${avd_name} &
    ${ADB} -e wait-for-device
}

function stop_android_emulators() {
    printf "\n> Stopping android emulators...\n"

    ${ADB} devices | grep emualtor | cut -f1 | while read line; do ${ADB} -s ${line} emu kill; done
}

function run_connected_android_test() {
    local avd_name=$1

    stop_android_emulators

    start_android_emulator ${avd_name}

    printf "\n> Running connected android test on [$avd_name] for [$MODULE_NAME:$MODULE_VERSION]."

    printf "\n${GRADLE} :${MODULE_NAME}:connectedAndroidTest\n"

    ANDROID_SERIAL=${EMULATOR_ID} ${GRADLE} :${MODULE_NAME}:connectedAndroidTest || { stop_android_emulators; printf "\nError: android test failed on [$avd_name] for module [$MODULE_NAME:$MODULE_VERSION].\n"; exit 1; }

    stop_android_emulators
}

printf "\n> Available android virtual devices.\n"
${EMULATOR} -list-avds

avds_aar=(`${EMULATOR} -list-avds | tr '\n' ' '`)

for avd_name in "${avds_aar[@]}"; do
    run_connected_android_test ${avd_name}
done
