#! /bin/bash
git branch --list  >> .branches
cat .branches
rm .branches
echo -e "\nType the name of an above branch to use it, or type "new" to create a new branch (If you are Luke just push to master, if you are not just create a new branch with your name)\n"
read branch

#if [ $branch == "create" } ]
#then
#	echo -e "\n Branch Name:\n"
#	read createBranch
#	git checkout -b $createBranch
#	$createBranch = $branch
#else
#	git checkout $branch
#fi

case "$branch" in
    new) 
	echo -e "\nBranch Name:\n"
	read branchNew
	git checkout -b $branchNew;;
    *) git checkout $branch ;;
esac

git add --all
git add -uf
echo -e "\nEnter a description of you're changes or today's date:\n"
read commitMessage
echo -e "\n Publishing..."
git commit -m "$commitMessage"
git push -uf origin $branch
