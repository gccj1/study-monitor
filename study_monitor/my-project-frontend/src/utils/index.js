import {ElMessage, ElMessageBox} from "element-plus";
import {post} from "@/net";

function fitByUnit(value,unit){
    const units = ['B','KB','MB','GB','TB','PB']
    let index = units.indexOf(unit)
    while (((value < 1 && value !== 0) || value >= 1024) && (index >= 0 || index < units.length)) {
        if(value >= 1024) {
            value = value / 1024
            index = index + 1
        } else {
            value = value * 1024
            index = index - 1
        }
    }
    return `${parseInt(value)} ${units[index]}`
}
function copyIp(ip){
    navigator.clipboard.writeText(ip).then(() => ElMessage.success('复制成功') )
}
function percentageToStatus(percentage) {
    if(percentage < 50)
        return 'success'
    else if(percentage < 80)
        return 'warning'
    else
        return 'exception'
}
function rename(id, name, after) {
    ElMessageBox.prompt('请输入新的服务器主机名称', '修改名称', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        inputValue: name,
        inputPattern: /^[a-zA-Z0-9_\u4e00-\u9fa5]{1,10}$/,
        inputErrorMessage: '名称只能包含中英文字符、数字和下划线',
    }).then(({ value }) => post('/api/monitor/rename', {
            id: id,
            name: value
        }, () => {
            ElMessage.success('主机名称已更新')
            after()
        },(meg) => ElMessage.error(meg))
    )
}
export  {
    fitByUnit,copyIp,rename,percentageToStatus
}