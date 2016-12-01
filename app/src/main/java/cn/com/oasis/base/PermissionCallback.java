package cn.com.oasis.base;

/**
 * 运行时权限申请
 */
public interface PermissionCallback {
    void onPermissionSucess();

    void onPermissionFailure();
}