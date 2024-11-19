#! /bin/sh

old_id=$(cat ./scripts/old-id.txt)
new_id=$1

if [ "$old_id" != "$new_id" ]
then
    cd ./src/main/resources/${old_id}Resources/localization
    for dir in */; do
        if [ -d "$dir" ]; then
            cd "$dir"
            files=$(ls)
            for f in $files; do
                substr=${f#"$old_id"}
                mv $f "${new_id}${substr}"
            done
            cd ../
        fi
    done
    cd ../../
    mv ${old_id}Resources ${new_id}Resources
    cd ../java
    mv $old_id $new_id
    cd ../
    grep -rl $old_id . | xargs sed -i "s/$old_id/$new_id/g"
    grep -rl $old_id ../../scripts | xargs sed -i "s/$old_id/$new_id/g"
else
    echo "Mod ID is the same, not renaming"
fi
