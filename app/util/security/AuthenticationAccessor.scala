package util.security

trait AuthenticationAccessor {
  def getAuthentication(): AuthenticationProvider
}
