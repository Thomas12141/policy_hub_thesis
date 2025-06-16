package org.example.utils;

public class TestPolicies {
    public static final String EMPTY_STRING = "";
    public static final String NULL_POINTER = null;
    public static final String EMPTY_JSON = """
            {
            }""";
    public static final String WITHOUT_CONTEXT = """
                {
                    "@type": "Set",
                    "uid": "http://example.com/policy:1010",
                    "permission": [{
                        "target": "http://example.com/asset:9898.movie",
                        "action": "use"
                    }]
                }""";
    public static final String VALID_POLICY = """
                    {
                      "@context": "http://www.w3.org/ns/odrl.jsonld",
                      "@type": "Policy",
                      "uid": "http://example.com/policy:2024",
                      "permission": [{
                        "action": ["use"],
                        "target": "http://example.com/asset:1900"
                      }]
                    }
                """;
    public static final String MISSING_REQUIRED_UID = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Policy",
              "permission": [{
                "action": ["use"],
                "target": "http://example.com/asset:0000"
              }]
            }
        """;
    public static final String ODRL_EXAMPLE_1 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1010",
              "permission": [{
                "target": "http://example.com/asset:9898.movie",
                "action": "use"
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_2 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Offer",
              "uid": "http://example.com/policy:1011",
              "profile": "http://example.com/odrl:profile:01",
              "permission": [{
                "target": "http://example.com/asset:9898.movie",
                "assigner": "http://example.com/party:org:abc",
                "action": "play"
              }]
            }
        """;
    public static final String ODRL_EXAMPLE_3 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Agreement",
              "uid": "http://example.com/policy:1012",
              "profile": "http://example.com/odrl:profile:01",
              "permission": [{
                "target": "http://example.com/asset:9898.movie",
                "assigner": "http://example.com/party:org:abc",
                "assignee": "http://example.com/party:person:billie",
                "action": "play"
              }]
            }
        """;
    public static final String ODRL_EXAMPLE_4 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Offer",
              "uid": "http://example.com/policy:3333",
              "profile": "http://example.com/odrl:profile:02",
              "permission": [{
                "target": "http://example.com/asset:3333",
                "action": "display",
                "assigner": "http://example.com/party:0001"
              }]
            }
        """;
    public static final String ODRL_EXAMPLE_5 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Policy",
              "uid": "http://example.com/policy:1011",
              "profile": "http://example.com/odrl:profile:03",
              "permission": [{
                "target": {
                  "@type": "AssetCollection",
                  "uid":  "http://example.com/archive1011" },
                "action": "index",
                "summary": "http://example.com/x/database"
              }]
            }
        """;// 6 and 7 are not policy examples
    public static final String ODRL_EXAMPLE_8 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Agreement",
              "uid": "http://example.com/policy:8888",
              "profile": "http://example.com/odrl:profile:04",
              "permission": [{
                "target": "http://example.com/music/1999.mp3",
                "assigner": "http://example.com/org/sony-music",
                "assignee": "http://example.com/people/billie",
                "action": "play"
              }]
            }
        """;
    public static final String ODRL_EXAMPLE_9 = """
            {
              "@context": [
                "http://www.w3.org/ns/odrl.jsonld",
                {
                  "vcard": "http://www.w3.org/2006/vcard/ns#"
                }
              ],
              "@type": "Agreement",
              "uid": "http://example.com/policy:777",
              "profile": "http://example.com/odrl:profile:05",
              "permission": [
                {
                  "target": "http://example.com/looking-glass.ebook",
                  "assigner": {
                    "@type": [
                      "Party",
                      "vcard:Organization"
                    ],
                    "uid": "http://example.com/org/sony-books",
                    "vcard:fn": "Sony Books LCC",
                    "vcard:hasEmail": "sony-contact@example.com"
                  },
                  "assignee": {
                    "@type": [
                      "PartyCollection",
                      "vcard:Group"
                    ],
                    "uid": "http://example.com/team/A",
                    "vcard:fn": "Team A",
                    "vcard:hasEmail": "teamA@example.com"
                  },
                "action": "use"
                }
              ]
            }
    """;// 10-11 are not policies
    public static final String ODRL_EXAMPLE_12 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Offer",
              "uid": "http://example.com/policy:1012",
              "profile": "http://example.com/odrl:profile:06",
              "permission": [{
                "target": "http://example.com/music:1012",
                "assigner": "http://example.com/org:abc",
                "action": "play"
              }]
            }
            """;
    public static final String ODRL_EXAMPLE_13 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Offer",
              "uid": "http://example.com/policy:6163",
              "profile": "http://example.com/odrl:profile:10",
              "permission": [{
                "target": "http://example.com/document:1234",
                "assigner": "http://example.com/org:616",
                "action": "distribute",
                "constraint": [{
                  "leftOperand": "dateTime",
                  "operator": "lt",
                  "rightOperand":  { "@value": "2018-01-01", "@type": "xsd:date" }
                }]
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_14 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Offer",
              "uid": "http://example.com/policy:6161",
              "profile": "http://example.com/odrl:profile:10",
              "permission": [{
                "target": "http://example.com/document:1234",
                "assigner": "http://example.com/org:616",
                "action": [{
                  "rdf:value": { "@id": "odrl:print" },
                  "refinement": [{
                    "leftOperand": "resolution",
                    "operator": "lteq",
                    "rightOperand": { "@value": "1200", "@type": "xsd:integer" },
                    "unit": "http://dbpedia.org/resource/Dots_per_inch"
                  }]
                }]
              }]
            }
    """;// 15 is not supported yet
    public static final String ODRL_EXAMPLE_16 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Offer",
              "uid": "http://example.com/policy:4444",
              "profile": "http://example.com/odrl:profile:11",
              "permission": [{
                "assigner": "http://example.com/org88",
                "target": {
                  "@type": "AssetCollection",
                  "source":  "http://example.com/media-catalogue",
                  "refinement": [{
                    "leftOperand": "runningTime",
                    "operator": "lt",
                    "rightOperand": { "@value": "60", "@type": "xsd:integer" },
                    "unit": "http://qudt.org/vocab/unit/MinuteTime"
                  }]
                },
                "action": "play"
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_17 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Agreement",
              "uid": "http://example.com/policy:4444",
              "profile": "http://example.com/odrl:profile:12",
              "permission": [{
                "target": "http://example.com/myPhotos:BdayParty",
                "assigner": "http://example.com/user44",
                "assignee": {
                  "@type": "PartyCollection",
                  "source":  "http://example.com/user44/friends",
                  "refinement": [{
                    "leftOperand": "foaf:age",
                    "operator": "gt",
                    "rightOperand": { "@value": "17", "@type": "xsd:integer" }
                  }]
                },
                "action": { "@id": "ex:view" }
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_18 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Offer",
              "uid": "http://example.com/policy:9090",
              "profile": "http://example.com/odrl:profile:07",
              "permission": [{
                "target": "http://example.com/game:9090",
                "assigner": "http://example.com/org:xyz",
                "action": "play",
                "constraint": [{
                  "leftOperand": "dateTime",
                  "operator": "lteq",
                  "rightOperand": { "@value": "2017-12-31", "@type": "xsd:date" }
                }]
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_19 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Agreement",
              "uid": "http://example.com/policy:5555",
              "profile": "http://example.com/odrl:profile:08",
              "conflict": "perm",
              "permission": [{
                "target": "http://example.com/photoAlbum:55",
                "action": "display",
                "assigner": "http://example.com/MyPix:55",
                "assignee": "http://example.com/assignee:55"
              }],
              "prohibition": [{
                "target": "http://example.com/photoAlbum:55",
                "action": "archive",
                "assigner": "http://example.com/MyPix:55",
                "assignee": "http://example.com/assignee:55"
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_20 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Agreement",
              "uid": "http://example.com/policy:42",
              "profile": "http://example.com/odrl:profile:09",
              "obligation": [{
                "assigner": "http://example.com/org:43",
                "assignee": "http://example.com/person:44",
                "action": [{
                  "rdf:value": {
                    "@id": "odrl:compensate"
                  },
                  "refinement": [
                  {
                    "leftOperand": "payAmount",
                    "operator": "eq",
                    "rightOperand": { "@value": "500.00", "@type": "xsd:decimal" },
                    "unit": "http://dbpedia.org/resource/Euro"
                  }]
                }]
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_21 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Agreement",
              "uid": "http://example.com/policy:42",
              "profile": "http://example.com/odrl:profile:09",
              "obligation": [{
                "assigner": "http://example.com/org:43",
                "assignee": "http://example.com/person:44",
                "action": [{
                  "rdf:value": {
                    "@id": "odrl:compensate"
                  },
                  "refinement": [{
                    "leftOperand": "payAmount",
                    "operator": "eq",
                    "rightOperand": { "@value": "500.00", "@type": "xsd:decimal" },
                    "unit": "http://dbpedia.org/resource/Euro"
                  }]
                }]
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_22 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Offer",
              "uid": "http://example.com/policy:88",
              "profile": "http://example.com/odrl:profile:09",
              "permission": [{
                "assigner": "http://example.com/assigner:sony",
                "target": "http://example.com/music/1999.mp3",
                "action": "play",
                "duty": [{
                  "action": [{
                    "rdf:value": { "@id": "odrl:compensate" },
                    "refinement": [{
                    "leftOperand": "payAmount",
                    "operator": "eq",
                    "rightOperand": { "@value": "5.00", "@type": "xsd:decimal" },
                    "unit": "http://dbpedia.org/resource/Euro"
                    }]
                  }],
                  "constraint": [{
                    "leftOperand": "event",
                    "operator": "lt",
                    "rightOperand": { "@id": "odrl:policyUsage" }
                  }]
                }]
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_23 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Agreement",
              "uid": "http://example.com/policy:66",
              "profile": "http://example.com/odrl:profile:09",
              "permission": [{
                "target": "http://example.com/data:77",
                "assigner": "http://example.com/org:99",
                "assignee": "http://example.com/person:88",
                "action": "distribute",
                "duty": [{
                  "action": "attribute",
                  "attributedParty": "http://australia.gov.au/",
                  "consequence": [{
                    "action": "acceptTracking",
                    "trackingParty": "http://example.com/dept:100"
                  }]
                }]
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_24 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Agreement",
              "uid": "http://example.com/policy:33CC",
              "profile": "http://example.com/odrl:profile:09",
              "prohibition": [{
                "target": "http://example.com/data:77",
                "assigner": "http://example.com/person:88",
                "assignee": "http://example.com/org:99",
                "action": "index",
                "remedy": [{
                  "action": "anonymize",
                  "target": "http://example.com/data:77"
                }]
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_25 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Policy",
              "uid": "http://example.com/policy:7777",
              "profile": "http://example.com/odrl:profile:20",
              "permission": [{
                "target": "http://example.com/music/1999.mp3",
                "assigner": "http://example.com/org/sony-music",
                "action": "play"
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_26 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Policy",
              "uid": "http://example.com/policy:8888",
              "profile": "http://example.com/odrl:profile:20",
              "permission": [{
                "target": [ "http://example.com/music/1999.mp3",
                  "http://example.com/music/PurpleRain.mp3" ],
                "assigner": "http://example.com/org/sony-music",
                "action": [ "play", "stream" ]
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_27 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Policy",
              "uid": "http://example.com/policy:8888",
              "profile": "http://example.com/odrl:profile:20",
              "permission": [{
                "target": "http://example.com/music/1999.mp3",
                "assigner": "http://example.com/org/sony-music",
                "action": "play"
              },
              {
                "target": "http://example.com/music/1999.mp3",
                "assigner": "http://example.com/org/sony-music",
                "action": "stream"
              },
              {
                "target": "http://example.com/music/PurpleRain.mp3",
                "assigner": "http://example.com/org/sony-music",
                "action": "play"
              },
              {
                "target": "http://example.com/music/PurpleRain.mp3",
                "assigner": "http://example.com/org/sony-music",
                "action": "stream"
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_28 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Policy",
              "uid": "http://example.com/policy:8888",
              "profile": "http://example.com/odrl:profile:21",
              "target": "http://example.com/music/1999.mp3",
              "assigner": "http://example.com/org/sony-music",
              "action": "play",
              "permission": [{
                "assignee": "http://example.com/people/billie"
              },
              {
                "assignee": "http://example.com/people/murphy"
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_29 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Policy",
              "uid": "http://example.com/policy:8888",
              "profile": "http://example.com/odrl:profile:21",
              "permission": [{
                "assignee": "http://example.com/people/billie",
                "target": "http://example.com/music/1999.mp3",
                "assigner": "http://example.com/org/sony-music",
                "action": "play"
              },
              {
                "assignee": "http://example.com/people/murphy",
                "target": "http://example.com/music/1999.mp3",
                "assigner": "http://example.com/org/sony-music",
                "action": "play",
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_30 = """
            {
              "@context": [
                "http://www.w3.org/ns/odrl.jsonld",
                { "dc": "http://purl.org/dc/terms/" }
              ],
              "@type": "Policy",
              "uid": "http://example.com/policy:8888",
              "profile": "http://example.com/odrl:profile:22",
              "dc:creator": "Billie Enterprises LLC",
              "dc:description": "This policy covers...",
              "dc:issued": "2017-01-01T12:00",
              "dc:coverage": { "@id": "https://www.iso.org/obp/ui/#iso:code:3166:AU-QLD" },
              "dc:replaces": { "@id": "http://example.com/policy:8887" },
              "permission": [ { } ]
            }
    """;
    public static final String ODRL_EXAMPLE_31 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Policy",
              "uid": "http://example.com/policy:default",
              "profile": "http://example.com/odrl:profile:30",
              "assigner": "http://example.com/org-01",
              "obligation": [{
                "target": "http://example.com/asset:terms-and-conditions",
                "action": "reviewPolicy"
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_32 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Agreement",
              "uid": "http://example.com/policy:4444",
              "profile": "http://example.com/odrl:profile:30",
              "inheritFrom": "http://example.com/policy:default",
              "assignee": "http://example.com/user:0001",
              "permission": [{
                "target": "http://example.com/asset:5555",
                "action":  "display"
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_33 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Agreement",
              "uid": "http://example.com/policy:4444",
              "profile": "http://example.com/odrl:profile:30",
              "inheritFrom": "http://example.com/policy:default",
              "permission": [{
                "target": "http://example.com/asset:5555",
                "action": "display",
                "assigner": "http://example.com/org-01",
                "assignee": "http://example.com/user:0001"
              }],
              "obligation": [{
                "target": "http://example.com/asset:terms-and-conditions",
                "action": "reviewPolicy",
                "assigner": "http://example.com/org-01",
                "assignee": "http://example.com/user:0001"
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_34 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Policy",
              "uid": "http://example.com/policy:0001",
              "profile": "http://example.com/odrl:profile:40",
              "conflict": "perm",
              "permission": [{
                "target": "http://example.com/asset:1212",
                "action": "use",
                "assigner": "http://example.com/owner:181"
              }]
            }
    """;
    public static final String ODRL_EXAMPLE_35 = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Policy",
              "uid": "http://example.com/policy:0002",
              "profile": "http://example.com/odrl:profile:40",
              "conflict": "perm",
              "permission": [{
                "target": "http://example.com/asset:1212",
                "action": "display",
                "assigner": "http://example.com/owner:182"
              }],
              "prohibition": [{
                "target": "http://example.com/asset:1212",
                "action": "print"
              }]
            }
    """;
}
