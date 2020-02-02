#!/bin/bash

#Pull from Github repository chosen

function pullfunc {
 
read yesOrno


case $yesOrno in 
    yes)
	git branch --list > .branches
	cat .branches
	rm .branches
	echo -e "\nThe branch with the "*" is the branch currently being used, select new branch\n"
	read branch
	git checkout $branch
	git pull -f $branch
	echo -e "Now using branch $branch, have fun!"
    ;;
    no)
	echo -e "\nNo problem!"
    ;;
    *) 
	echo -e "\nPlease enter 'yes' or 'no'"
	echo $yesOrno
	pullfunc
    ;;
esac
}


echo -e "Are you sure you want to pull? This will replace whatever code you have in your repository (enter yes or no)\n"

pullfunc
