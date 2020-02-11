#!/bin/bash
echo -e "What is your name?"
read name
date='date +%m/%d/%Y'
git checkout -B ${date}-$name
git add --all
echo -e "This should push code from your computer to the master (default) branch of the git repository as well as create a new branch for today, for backup\n"
echo -e "\nCommit message, (type 'skip' if you used Android studio to create the commit" 
read commitMessage
case $commitMessage in
        skip) 
        ;;

        *)	git add --all
		git commit -m "$commitMessage"
esac

git push -u origin ${date}-$name
echo -e "\nMerge with master (yes/no)\n"
case $yesorno in
	yes)
		git merge ${date}-$name
		git checkout master
		git add --all
		git commit -m "merged from ${date}-$name"
		git push -u origin master
		;;
	no)

		;;
	*)
		echo "Invalid Response";;
esac
