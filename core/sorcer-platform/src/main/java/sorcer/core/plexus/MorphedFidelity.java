/*
* Copyright 2016 SORCERsoft.org.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package sorcer.core.plexus;

import net.jini.core.transaction.TransactionException;
import net.jini.id.Uuid;
import net.jini.id.UuidFactory;
import sorcer.core.invoker.Observable;
import sorcer.service.*;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Mike Sobolewski on 04/26/16.
 */
public class MorphedFidelity<T extends Arg> extends Observable implements
        Identifiable, Multifidelity<T>, Arg {

    // fidelity of fidelities T  that is observable
    private ServiceFidelity<T> fidelity;

    private Morpher morpher;

    private Uuid id = UuidFactory.generate();

    public MorphedFidelity(ServiceFidelity fi) {
        fidelity = fi;
    }

    public MorphedFidelity(FidelityManager manager) {
        addObserver(manager);
    }

    public ServiceFidelity<T> getFidelity() {
        return fidelity;
    }

    public void setFidelity(ServiceFidelity<T> fi) {
        this.fidelity = fi;
    }

    public T getSelect() {
        return fidelity.getSelect();
    }

    @Override
    public void setSelect(String name) {
        fidelity.setSelect(name);
    }

    @Override
    public void addSelect(T fidelity) {
        this.fidelity.addSelect(fidelity);
    }

    public void setSelect(T selection) {
        fidelity.setSelect(selection);
    }

    public T getSelect(String name) {
        return fidelity.getSelect(name);
    }

    public List<T> getSelects() {
        return fidelity.getSelects();
    }

    public void setSelects(List<T> selects) {
        fidelity.setSelects(selects);;
    }

    public String getPath() {
        return fidelity.getPath();
    }

    public void setPath(String fidelityPath) {
        fidelity.setPath(fidelityPath);;
    }

    @Override
    public Object exec(Arg... args) throws ServiceException, RemoteException, TransactionException {
        if (fidelity.getSelect() instanceof Service) {
            return ((Service)fidelity.getSelect()).exec(args);
        } else return fidelity.getSelect();
    }

    @Override
    public Object getId() {
        return id;
    }

    public String getName() {
        return fidelity.getName();
    }

    public void setName(String name) {
        fidelity.setName(name);
    }

    public Morpher getMorpher() {
        return morpher;
    }

    public void setMorpher(Morpher morpher) {
        this.morpher = morpher;
    }

    @Override
    public String toString() {
        return fidelity.getName() + (fidelity.getPath() != null ?
                "@" + fidelity.getPath() + " " : " ") + fidelity.getSelects();
    }

    public int size() {
        return fidelity.getSelects().size();
    }

}
