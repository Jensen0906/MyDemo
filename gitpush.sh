if [ $1 != -msg ]; then
    echo -e "请输入正确的命令\n"
    echo "不带参数       只提交时间"
    echo "-msg 提交时间和自定义信息"
    exit 1
fi
if [ $1 == -msg ]; then
    while [ $# -gt 0 ]; do
        case "$1" in
            -msg)
                shift
                if [ -n "$1" ]; then
                    # 输出 -msg 后的参数，包括空格
                    echo "$1"
                    msg="$1"
                else
                    echo "未提供 -msg 参数的值"
                    exit 1
                fi
                ;;
            *)
                # 忽略其他参数
                shift
                ;;
        esac
    done
    git status .
    git add .
    git status .
    git commit -m "${time}---${msg}"
    git push
fi
