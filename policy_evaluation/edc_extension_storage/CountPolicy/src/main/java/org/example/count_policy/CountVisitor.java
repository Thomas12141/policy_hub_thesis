package org.example.count_policy;

import org.eclipse.edc.policy.model.Duty;
import org.eclipse.edc.policy.model.Permission;
import org.eclipse.edc.policy.model.Prohibition;
import org.eclipse.edc.policy.model.Rule;

import java.util.List;

public class CountVisitor  implements Rule.Visitor<Boolean> {
    private final int timesTransferred;

    public CountVisitor(int timesTransferred) {
        this.timesTransferred = timesTransferred;
    }

    @Override
    public Boolean visitPermission(Permission permission) {
        return null;
    }

    @Override
    public Boolean visitProhibition(Prohibition prohibition) {
        return null;
    }

    @Override
    public Boolean visitDuty(Duty duty) {
        return null;
    }
}
