#!/bin/bash
: '
This script creates a new Kotlin Multiplatform module.
You can provide your module name(required), package name(required) and an optional directory in
which the module should be created. If no directory is provided, then a new module will be created
in the root - TimeToWish-App.

Usage without directory:
create_shared_module.sh moduleName my.module.package.name

Usage with directory:
create_shared_module.sh moduleName my.module.package.name directory
'
#check args
if [ -z "$1" ] || [ -z "$2" ]; then
  printf "Please provide the module name and package name\n"
  printf "Usage without directory:\ncreate_shared_module.sh moduleName my.module.package.name\n"
  printf "Usage with directory:\ncreate_shared_module.sh moduleName my.module.package.name directory\n"
  exit 1
fi

#take args
name=$1
package=$2
test_package="$2.test"
if [ -z "$3" ]; then
  directory=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && cd .. && pwd )
else
  directory=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && cd .. && pwd )/$3
fi
base_path=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/basefiles
root_directory=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && cd .. && pwd )

echo "Creating module $name with package $package in directory $directory"

#create new module directory
cd "$directory" || exit
mkdir "$name"

module_dir=$directory/$name
cd "$name" || exit

#create gradle file
cp "$base_path"/build.gradle.kts "$module_dir"
sed -i '' "s|moduleName|$name|g" build.gradle.kts
sed -i '' "s|modulePackage|$package|g" build.gradle.kts

#create commonMain and commonTest
mkdir -p src/commonMain/kotlin/"$package"
mkdir -p src/commonTest/kotlin/"$test_package"

#add module to settings.gradle.kts
printf '\ninclude("%s")' "$name" >> "$root_directory"/settings.gradle.kts
