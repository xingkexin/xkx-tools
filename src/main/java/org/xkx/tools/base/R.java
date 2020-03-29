package org.xkx.tools.base;

import lombok.Data;

/**
 * json结果标准结构对象
 * @param <T>
 */
@Data
public class R<T> {
   private String code;
   private String msg;
   private T data;

   public R(){
      this.code = CodeEnum.SUCCESS.getCode();
      this.msg = CodeEnum.SUCCESS.getMsg();
   }

   public void setCodeEnum(CodeEnum e){
      this.code = e.getCode();
      this.msg = e.getMsg();
   }

   public R<T> error(String code, String msg){
      this.code = code;
      this.msg = msg;
      return this;
   }

   public enum CodeEnum{
      SUCCESS("200", "成功"), PARAM_ERROR("401", "参数错误"), ERROR("999", "未知异常");

      private String code;
      private String msg;
      CodeEnum(String code, String msg){
         this.code = code;
         this.msg = msg;
      }

      public String getCode() {
         return code;
      }

      public String getMsg() {
         return msg;
      }
   }
}

