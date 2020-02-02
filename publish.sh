#! /bin/bash


#add files automatically
git add --all
git add -uf
echo -e "\nEnter a description of you're changes or today's date:\n"
read commitMessage
git commit -m "$commitMessage"


#choose branch
git branch --list  >> .branches
cat .branches
rm .branches
echo -e "\nType the name of an above branch to use it, or type "new" to create a new branch (If you are Luke just push to master, if you are not just create a new branch with your name)\n"
read branch

case "$branch" in
    new) 
	echo -e "\nBranch Name:\n"
	read branchNew
	git checkout -b $branchNew
	git push -uf origin $branchNew
    ;;
    master)
	echo -e "\n Be careful, this can delete other's work, for this reason all commits to master should have a backup branch, just in case somebody accidentally overwrites your code, enter your name to automatically create this backup or enter "no" to skip this\n"
	read response
	case "$response" in
	    no)
		git checkout master
		git push -uf origin master
	    ;;
	    *)
		git checkout -b $response
		git push -uf origin $response
	    ;;
	;;
    *)
	git checkout $branch 
	git push -uf origin $branch
    ;;
esac

