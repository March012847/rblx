package com.android.virtualization.terminal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.virtualization.terminal.IInstallProgressListener;

public interface IInstallerService extends IInterface {
    boolean isInstalled();

    boolean isInstalling();

    void requestInstall(boolean z);

    void setProgressListener(IInstallProgressListener iInstallProgressListener);

    public abstract class Stub extends Binder implements IInstallerService {
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.android.virtualization.terminal.IInstallerService");
        }

        public static IInstallerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.virtualization.terminal.IInstallerService");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IInstallerService)) {
                return new Proxy(iBinder);
            }
            return (IInstallerService) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.virtualization.terminal.IInstallerService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.virtualization.terminal.IInstallerService");
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                requestInstall(readBoolean);
                parcel2.writeNoException();
            } else if (i == 2) {
                IInstallProgressListener asInterface = IInstallProgressListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setProgressListener(asInterface);
                parcel2.writeNoException();
            } else if (i == 3) {
                boolean isInstalling = isInstalling();
                parcel2.writeNoException();
                parcel2.writeBoolean(isInstalling);
            } else if (i != 4) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                boolean isInstalled = isInstalled();
                parcel2.writeNoException();
                parcel2.writeBoolean(isInstalled);
            }
            return true;
        }

        class Proxy implements IInstallerService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public void requestInstall(boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.virtualization.terminal.IInstallerService");
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setProgressListener(IInstallProgressListener iInstallProgressListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.virtualization.terminal.IInstallerService");
                    obtain.writeStrongInterface(iInstallProgressListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isInstalling() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.virtualization.terminal.IInstallerService");
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isInstalled() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.virtualization.terminal.IInstallerService");
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
