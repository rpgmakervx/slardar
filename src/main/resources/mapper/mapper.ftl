{
  "findByUser": {
    "sql":
    "select * from user where 1 = 1
      <#if username==''>
      and username = $username
      </#if>
      <#if phone == ''>
      and phone = $phone
      </#if>
    "
  }
}