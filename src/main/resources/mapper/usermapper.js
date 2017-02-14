/**
 * api:
 * js默认的对象：ctx
 * ctx.where: 默认值为where 1 = 1 用
 *            来拼接 sql语句，减少是否需要加and的判断操作
 * ctx.namespace 用默认为空，来指定js的namespace
 */
ctx.namespace = "org.easyarch.test.dao.UserMapper";

function findById(params){
    return "select * from user where client_id = $id$";
}
function getCount(params){
    var sql = "select count(1) from user" + ctx.where;
    if (params.clientId != undefined){
        sql += " and client_id = $clientId$";
    }
    if (params.userName != undefined){
        sql += " and username = $userName$";
    }
    if (params.phone != undefined){
        sql += " and phone = $phone$";
    }
    return sql;
}

function findByUser(params) {
    var sql = "select * from user" + ctx.where;
    if (params.clientId != undefined){
        sql += " and client_id = $clientId$"
    }
    if (params.userName != undefined){
        sql += " and username = $userName$";
    }
    if (params.phone != undefined){
        sql += " and phone = $phone$";
    }
    return sql;
}

function insert(params){
    return "insert into user(client_id,username,password,phone) " +
        "values($clientId$,$userName$,$password$,$phone$)"
}

function update(params){
    var sql = "update user set ";
    if (params.userName != undefined){
        sql += "username = $userName$"
    }
    if (params.phone != undefined){
        sql += ", phone = $phone$"
    }
    if(params.password != undefined){
        sql += " , password = $password$"
    }
    sql += ctx.where + " and client_id = $clientId$";
    return sql;
}

function deleteById(params){
    return "delete from user where client_id = $clientId$";
}