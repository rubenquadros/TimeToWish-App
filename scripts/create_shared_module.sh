#!/bin/bash
: '
This script creates a new Kotlin Multiplatform module.
You can provide your module name(required), package name(required) and an optional directory in
which the module should be created. If no directory is provided, then a new module will be created
in the root - TimeToWish-App.

Flag information:
-d: If your module will use the database. If it is not specified then database related functionality is not added in the module gradle
-m: Module name (Required)
-p: Package name (Required)
-r: If you want to specify a directory. If it is not specified then the new module will be created in the root directory

Usage without directory:
create_shared_module.sh -d -m moduleName -p my.module.package.name

Usage with directory:
create_shared_module.sh -d -m moduleName -p my.module.package.name -r directory
'
show_usage_and_exit() {
cat << HELP
    Usage:
    sh create_shared_module -d -m moduleName -p packageName -r moduleDirectory
    -d: If your module will use the database. If it is not specified then database related functionality is not added in the module gradle
    -m: Module name (Required)
    -p: Package name (Required)
    -r: If you want to specify a directory. If it is not specified then the new module will be created in the root directory
HELP
exit 1
}
#read
use_database=false
module_name=""
package_name=""
directory_name=""
while getopts "drm:p:" opt; do
  case $opt in
    d)
      use_database=true
      ;;
    m)
      module_name="$OPTARG"
      ;;
    p)
      package_name="$OPTARG"
      ;;
    r)
      directory_name="$OPTARG"
      ;;
    *)
      show_usage_and_exit
      ;;
  esac
done

#check args
if [ -z "$module_name" ] || [ -z "$package_name" ]; then
  show_usage_and_exit
fi

#take args
test_package="$package_name.test"
if [ -z "$directory_name" ]; then
  directory=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && cd .. && pwd )
  gradle_module=''
  compose_app_module=''
else
  directory=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && cd .. && pwd )/$directory_name
  gradle_module="$directory_name:"
  compose_app_module="$directory_name."
fi
base_path=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )/basefiles
root_directory=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && cd .. && pwd )

echo "Creating module $module_name with package $package_name in directory $directory"

#create new module directory
cd "$directory" || mkdir "$directory" && cd "$directory" || exit
mkdir "$module_name"

module_dir=$directory/$module_name
cd "$module_name" || exit

#create gradle file
if [ "$use_database" = "true" ]; then
  cp "$base_path"/build.db.gradle.kts "$module_dir"/build.gradle.kts
else
  cp "$base_path"/build.gradle.kts "$module_dir"/build.gradle.kts
fi
sed -i '' "s|moduleName|$module_name|g" build.gradle.kts
sed -i '' "s|modulePackage|$package_name|g" build.gradle.kts

#create commonMain and commonTest
mkdir -p src/commonMain/kotlin/"$package_name"
mkdir -p src/commonTest/kotlin/"$test_package"

#add module to settings.gradle.kts
printf '\ninclude("%s")' ":$gradle_module$module_name" >> "$root_directory"/settings.gradle.kts

#add module to composeApp
#add db dependency to composeApp
cd "$root_directory/composeApp" || exit

sed -i '' "/featureModules/a\\
implementation(projects.$compose_app_module$module_name)
" build.gradle.kts

if [ "$use_database" = "true" ]; then
    sed -i '' "/generateAsync.set/a\\
    dependency(projects.$compose_app_module$module_name)
" build.gradle.kts
fi
