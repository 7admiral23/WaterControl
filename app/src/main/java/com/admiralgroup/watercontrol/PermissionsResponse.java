package com.admiralgroup.watercontrol;

import java.io.Serializable;
import java.util.Map;

public class PermissionsResponse implements Serializable {
    private Map<String, Action> actions;

    public Map<String, Action> getActions() {
        return actions;
    }

    public void setActions(Map<String, Action> actions) {
        this.actions = actions;
    }

    public static class Action implements Serializable {
        private String label;
        private Map<String, Command> items;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Map<String, Command> getItems() {
            return items;
        }

        public void setItems(Map<String, Command> items) {
            this.items = items;
        }

        public static class Command implements Serializable {
            private String label;
            private String payload;
            private Map<String, Parameter> parameters;

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getPayload() {
                return payload;
            }

            public void setPayload(String payload) {
                this.payload = payload;
            }

            public Map<String, Parameter> getParameters() {
                return parameters;
            }

            public void setParameters(Map<String, Parameter> parameters) {
                this.parameters = parameters;
            }

            public static class Parameter implements Serializable {
                private String label;
                private String type;
                private String value;
                private String required;

                public String getLabel() {
                    return label;
                }

                public void setLabel(String label) {
                    this.label = label;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getRequired() {
                    return required;
                }

                public void setRequired(String required) {
                    this.required = required;
                }
            }
        }
    }
}