#!/bin/bash
#Choose to push or pull code


function PushOrPull {
#echo -e "\nEnter pull to update the code stored on your computer, or push to update the repository with code from your computer\n">
read pushorpull

case $pushorpull in
    push)
        exec bash publish.sh
    ;;
    pull)
        exec bash pull.sh
    ;;
    *)
        echo -e "\nPlease choose push or pull\n"
        PushOrPull
    ;;
esac
}



echo -e "\nAre you trying to update the code stored on your computer (pull), or push code on your computer to the repository (push)?\n"

PushOrPull
