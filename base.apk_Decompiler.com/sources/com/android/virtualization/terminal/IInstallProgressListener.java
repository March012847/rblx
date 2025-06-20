package com.android.virtualization.terminal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public interface IInstallProgressListener extends IInterface {
    void onCompleted();

    void onError(String str);

    public abstract class Stub extends Binder implements IInstallProgressListener {
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.android.virtualization.terminal.IInstallProgressListener");
        }

        public static IInstallProgressListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.virtualization.terminal.IInstallProgressListener");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IInstallProgressListener)) {
                return new Proxy(iBinder);
            }
            return (IInstallProgressListener) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.virtualization.terminal.IInstallProgressListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.virtualization.terminal.IInstallProgressListener");
                return true;
            }
            if (i == 1) {
                onCompleted();
            } else if (i != 2) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onError(readString);
            }
            return true;
        }

        class Proxy implements IInstallProgressListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void onCompleted() {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.virtualization.terminal.IInstallProgressListener");
                    this.mRemote.transact(1, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void onError(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("com.android.virtualization.terminal.IInstallProgressListener");
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
