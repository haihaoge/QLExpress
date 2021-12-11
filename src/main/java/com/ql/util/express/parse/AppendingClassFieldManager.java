package com.ql.util.express.parse;

import java.util.ArrayList;
import java.util.List;

import com.ql.util.express.InstructionSetContext;
import com.ql.util.express.Operator;

/**
 * Created by tianqiao on 16/10/16.
 */
public class AppendingClassFieldManager {
    private final List<AppendingField> appendingFields = new ArrayList<>();

    public void addAppendingField(String name, Class<?> bindingClass, Class<?> returnType, Operator op) {
        appendingFields.add(new AppendingField(name, bindingClass, returnType, op));
    }

    public AppendingField getAppendingClassField(Object object, String fieldName) {
        for (AppendingField appendingField : appendingFields) {
            //object是定义类型的子类
            if (fieldName.equals(appendingField.name) && (object.getClass() == appendingField.bindingClass
                || appendingField.bindingClass.isAssignableFrom(object.getClass()))) {
                return appendingField;
            }
        }
        return null;
    }

    public Object invoke(AppendingField appendingField, InstructionSetContext context, Object aFieldObject,
        List<String> errorList) throws Exception {
        Operator op = appendingField.op;
        return op.executeInner(new Object[] {aFieldObject});
    }

    public static class AppendingField {
        public final String name;

        public final Class<?> bindingClass;

        public final Operator op;

        public final Class<?> returnType;

        public AppendingField(String name, Class<?> bindingClass, Class<?> returnType, Operator op) {
            this.name = name;
            this.bindingClass = bindingClass;
            this.op = op;
            this.returnType = returnType;
        }
    }
}
