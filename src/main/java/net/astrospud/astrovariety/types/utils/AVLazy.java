//
// copied from the deprecated method
//

package net.astrospud.astrovariety.types.utils;

import com.google.common.base.Suppliers;
import java.util.Objects;
import java.util.function.Supplier;

public class AVLazy<T> {
    private Supplier<T> supplier;

    public void AVLazy(Supplier<T> delegate) {
        Objects.requireNonNull(delegate);
        this.supplier = Suppliers.memoize(delegate::get);
    }

    public AVLazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        return this.supplier.get();
    }
}
