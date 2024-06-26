#!/bin/bash
#######################################################################

# ---------------------------------------------------------------------
# Before we start...

# Check that we are in the correct directory
CURRDIR=$( pwd )
if [ ! -f "$CURRDIR/build.sh" ]
then
  echo "This build script has to be started in the directory where it is located."
  echo "Aborting."
  exit 1
fi

# ---------------------------------------------------------------------
# Core

echo "=============================================================="
echo "=============================================================="
echo "Building Packages"
echo "=============================================================="
echo "=============================================================="
  
# mvn package
mvn package -Dmaven.test.skip.exec
# mvn install

# ---------------------------------------------------------------------
# JavaDoc

echo ""
echo "=============================================================="
echo "=============================================================="
echo "Building JavaDoc"
echo "=============================================================="
echo "=============================================================="
  
for module in schnorxolib-base
do
  echo ""
  echo "=============================================================="
  echo "Module '$module'"
  echo "=============================================================="
  
  cd $module || exit 1
  rm -rf target/site
  mvn javadoc:javadoc
  cd ..
done
