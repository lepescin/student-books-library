package ru.lepescin.studentbookslibrary.util;

import lombok.experimental.UtilityClass;
import ru.lepescin.studentbookslibrary.error.IllegalRequestDataException;
import ru.lepescin.studentbookslibrary.model.AbstractBaseEntity;

@UtilityClass
public class ValidationUtil {
    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalRequestDataException(entity.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(AbstractBaseEntity entity, int id) {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.id() != id) {
            throw new IllegalRequestDataException(entity.getClass().getSimpleName() + " must has id=" + id);
        }
    }
}
