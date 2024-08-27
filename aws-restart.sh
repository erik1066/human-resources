#!/bin/bash
TASKS=$(aws ecs list-tasks)
TASK_ID=$(echo $TASKS | tail -c 37)
aws ecs stop-task --task $TASK_ID


