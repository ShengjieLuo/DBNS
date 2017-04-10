rm -f Interface*
protoc --java_out=. ./interface.proto
cp com/rpc/* .
mv Interface.java ../src/main/java/com/rpc
rm -rf com
