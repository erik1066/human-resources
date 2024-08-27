#!/bin/bash
#VAR=$(aws ecr get-login)
#VAR=$(aws ecr get-login | sed 's|https://||' | sed 's|-e none||')
VAR=$(aws ecr get-login | sed 's|-e none||')
$VAR