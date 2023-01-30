package account.utility;

//@Aspect
//@Component
public class LoggingAspect {

//    private Logger LOGGER = LoggerFactory.getLogger("security.event.logger");
//
//    @Autowired
//    private SecurityService securityService;
//
//    @Autowired
//    private HttpServletRequest request;
//
//    public String getUsernameFromHeader() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication auth = securityContext.getAuthentication();
//        if(auth == null){
//            return "Anonymous";
//        }
//        String username = auth.getName();
//        return username.contains("anonymousUser") ? "Anonymous" : username;
//    }
//
//    private void registerSecurityEvent(ActionType actionType, Object object) {
//        SecurityEvent securityEvent = new SecurityEvent();
//        securityEvent.setDate(LocalDate.now());
//        securityEvent.setAction(actionType);
//        securityEvent.setSubject(getUsernameFromHeader());
//        securityEvent.setObject((String) object);
//        securityEvent.setPath(request.getRequestURI());
//        securityService.registerEvent(securityEvent);
//        LOGGER.info(actionType.toString());
//
//    }
//
//    @Around(value = "execution(* account.controllers.AuthorisationController.register(..))")
//    public ResponseEntity<Account> afterCreatingUser(ProceedingJoinPoint joinPoint){
//        try {
//            String targetUsername = ((Account) joinPoint.getArgs()[0]).getEmail();
//            ResponseEntity<Account> response = (ResponseEntity<Account>) joinPoint.proceed();
//            registerSecurityEvent(ActionType.CREATE_USER, targetUsername);
//            return response;
//        } catch (Throwable e){
//            throw new GenericBadRequestException(e.getMessage());
//        }
//    }
//
//    @Around(value = "execution(* account.controllers.AdministratorController.deleteUser(..))")
//    public ResponseEntity<Map<String,String>> afterDeletingUser(ProceedingJoinPoint joinPoint){
//        try {
//            String object = joinPoint.getArgs()[0].toString();
//            ResponseEntity<Map<String,String>> response = (ResponseEntity<Map<String,String>>) joinPoint.proceed();
//            registerSecurityEvent(ActionType.DELETE_USER, object);
//            return response;
//        } catch (Throwable e){
//            throw new GenericBadRequestException(e.getMessage());
//        }
//    }
//
//
//
//    @Around(value = "execution(* account.controllers.AdministratorController.updateUserAccess(..))")
//    public ResponseEntity<Map<String,String>> afterUpdatingUserAccess(ProceedingJoinPoint joinPoint){
//        ModifyAccessDetails modifyAccessDetails = (ModifyAccessDetails) joinPoint.getArgs()[0];
//        try {
//            ResponseEntity<Map<String,String>> response = (ResponseEntity<Map<String,String>>) joinPoint.proceed();
//            String user = modifyAccessDetails.getUser();
//            String object = "";
//            if(modifyAccessDetails.getOperation().equals(AccessOperation.LOCK)){
//                object = String.format("Lock user %s", user);
//                registerSecurityEvent(ActionType.LOCK_USER, object);
//            } else if(modifyAccessDetails.getOperation().equals(AccessOperation.UNLOCK))  {
//                object = String.format("Unlock user %s", user);
//                registerSecurityEvent(ActionType.UNLOCK_USER, object);
//            }
//            return response;
//        } catch (Throwable e){
//            throw  new GenericBadRequestException(e.getMessage());
//        }
//    }
//
//    @Around(value = "execution(* account.controllers.AdministratorController.setUserRole(..))", argNames = "roleAssignDetails")
//    public ResponseEntity<Account>  afterUpdatingUserRoles(ProceedingJoinPoint joinPoint) throws Exception {
//        RoleAssignDetails roleAssignDetails =((RoleAssignDetails) joinPoint.getArgs()[0]);
//        OperationType operation = roleAssignDetails.getOperationType();
//        try{
//            ResponseEntity<Account>  response = (ResponseEntity<Account> ) joinPoint.proceed();
//            String role = roleAssignDetails.getRole();
//            String user = roleAssignDetails.getUser();
//            String object = "";
//            if(operation.equals(OperationType.GRANT)){
//                object = String.format("Grant role %s to %s", role , user);
//                registerSecurityEvent(ActionType.GRANT_ROLE, object);
//            } else if(operation.equals(OperationType.REMOVE))  {
//                object = String.format("Remove role %s from %s", role , user);
//                registerSecurityEvent(ActionType.REMOVE_ROLE, object);
//            }
//            return response;
//        } catch (Throwable e) {
//            throw new GenericBadRequestException(e.getMessage());
//        }
//    }
//
//    @Around(value = "execution(* account.services.SecurityServiceImpl.addFailedAttempt(..))")
//    public Integer afterAddFailedAttempt(ProceedingJoinPoint joinPoint){
//        try {
//            Integer noOfAttempts = (Integer) joinPoint.proceed();
//
//            registerSecurityEvent(ActionType.LOGIN_FAILED, request.getRequestURI());
//            if (noOfAttempts >= 5) {
//                registerSecurityEvent(ActionType.BRUTE_FORCE, request.getRequestURI());
//                registerSecurityEvent(ActionType.LOCK_USER, request.getRequestURI());
//            }
//            return noOfAttempts;
//        } catch (Throwable e){
//            throw new GenericBadRequestException(e.getMessage());
//        }
//    }
//
//
//    @Around(value = "execution(* account.config.CustomAccessDeniedHandler.handle(..))")
//    public void afterHandlingAccessDeniedEvent(ProceedingJoinPoint joinPoint){
//        try {
//            joinPoint.proceed();
//            registerSecurityEvent(ActionType.ACCESS_DENIED, request.getRequestURI());
//        } catch (Throwable e) {
//            throw new GenericBadRequestException(e.getMessage());
//        }
//    }

}
