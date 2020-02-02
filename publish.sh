#! /bin/bash
git branch --list  >> .branches
#echo $(<.branches)
echo -e "\nEnter the branch from above that you wish to use or create new branch (If you are Luke just push to master, if you are not just create a new branch with your name)\n"
git add --all
git add -uf
echo -e "\nEnter a description of you're changes or today's date:\n"
read commitMessage
echo -e "\n Publishing..."
git commit -m "$commitMessage"
git push -uf origin $branch
