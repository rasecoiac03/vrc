#/bin/bash

main_dir="$(pwd)/$(dirname $0)"

for p in $(cat $main_dir/properties_to_mongo)
do
	eval "curl -XPOST -d '$p' -H \"Content-Type: application/json\" http://localhost:9000/vrc/v1/properties"
	echo
done
