public enum OverlayResult {
  Yes, No, OK, Cancel, None;
  
  public boolean shouldDisplay() {
    return this != None;
  }
}
