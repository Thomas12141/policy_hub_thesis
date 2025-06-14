package org.example.drive.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Policy {
  private String uid;
  private String context;
  private String assignee;
  private String assigner;
  private Type type;
  private List<Permission> permissions;
  private List<Prohibition> prohibitions;
  private List<Obligation> obligations;
  private List<Object> otherProperties;
}
